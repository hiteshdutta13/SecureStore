package com.secure.store.service;

import com.secure.store.constant.GlobalConstants;
import com.secure.store.entity.Document;
import com.secure.store.entity.Folder;
import com.secure.store.entity.util.Status;
import com.secure.store.modal.Advisory;
import com.secure.store.modal.Response;
import com.secure.store.repository.DocumentRepository;
import com.secure.store.repository.FolderRepository;
import com.secure.store.repository.GlobalRepository;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl extends GlobalService implements FileService {

    @Autowired
    FolderRepository folderRepository;
    @Autowired
    GlobalRepository globalRepository;
    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Response upload(Long folderId, MultipartFile file) {
        var response = new Response();
        var preFix = globalRepository.findBy(GlobalConstants.KEYWORD_DOCUMENT_PATH_PREFIX);
        if(preFix.isPresent() && file != null) {
            try {
                var document = new Document();
                String path = FileUtil.docFilePath(preFix.get().getValue(), this.getUserId());
                String fileName = System.currentTimeMillis() + FileUtil.DOT + FileUtil.getFileExtension(file.getOriginalFilename());
                if (folderId > 0) {
                    path += folderRepository.getReferenceById(folderId).getPath();
                    document.setPath(folderRepository.getReferenceById(folderId).getPath());
                    var folder = new Folder();
                    folder.setId(folderId);
                    document.setFolder(folder);
                } else {
                    FileUtil.createDirectory(path);
                    document.setPath("/");
                }
                document.setName(fileName);
                document.setOriginalName(file.getOriginalFilename());
                document.setContentType(file.getContentType());
                document.setSize(file.getSize());
                document.setStatus(Status.Active);
                document.setUser(this.getUser());
                document.setCreatedDateTime(DateTimeUtil.currentDateTime());
                document.setUpdatedDateTime(document.getCreatedDateTime());
                if (FileUtil.write(file.getBytes(), path, fileName)) {
                    documentRepository.save(document);
                    response.setPersistId(document.getId());
                }else {
                    response = new Response(false);
                    var advisory = new Advisory();
                    advisory.setMessage("Error occurred during file write");
                    response.getAdvisories().add(advisory);
                }
            }catch (IOException ioException) {
                response = new Response(false);
                var advisory = new Advisory();
                advisory.setMessage(ioException.getMessage());
                response.getAdvisories().add(advisory);
            }
        }else {
            response = new Response(false);
            var advisory = new Advisory();
            advisory.setMessage("Document pre-fix path not configured");
            response.getAdvisories().add(advisory);
        }
        return response;
    }
}
