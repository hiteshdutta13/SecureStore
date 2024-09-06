package com.secure.store.service;

import com.secure.store.entity.File;
import com.secure.store.entity.util.Status;
import com.secure.store.modal.BinDTO;
import com.secure.store.modal.Response;
import com.secure.store.repository.FileRepository;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.EntityToModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinServiceImpl extends GlobalService implements BinService {

    @Autowired
    FileRepository fileRepository;
    @Override
    public BinDTO findAll() {
        var bin = new BinDTO();
        bin.setFiles(EntityToModelTransformer.transform(fileRepository.findByUser(this.getUserId(), Status.Deleted)));
        return bin;
    }

    @Override
    public Response delete(Long id) {
        fileRepository.deleteById(id);
        return new Response();
    }

    @Override
    public Response delete(List<Long> ids) {
        for (Long id: ids) {
            this.delete(id);
        }
        return new Response();
    }

    @Override
    public Response deleteAll() {
        this.delete(fileRepository.findByUser(this.getUserId(), Status.Deleted).stream().map(File::getId).collect(Collectors.toList()));
        return new Response();
    }

    @Override
    public Response restore(Long id) {
        File file = fileRepository.getReferenceById(id);
        file.setStatus(Status.Active);
        file.setUpdatedDateTime(DateTimeUtil.currentDateTime());
        fileRepository.save(file);
        return new Response();
    }

    @Override
    public Response restore(List<Long> ids) {
        for (Long id: ids) {
            this.restore(id);
        }
        return new Response();
    }

    @Override
    public Response restoreAll() {
        this.restore(fileRepository.findByUser(this.getUserId(), Status.Deleted).stream().map(File::getId).collect(Collectors.toList()));
        return new Response();
    }
}
