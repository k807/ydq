package group.ydq.service.service.impl;

import group.ydq.model.dao.dm.FileRepository;
import group.ydq.model.entity.dm.ProjectFile;
import group.ydq.service.service.FileService;
import group.ydq.utils.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Daylight
 * @date 2018/11/30 19:58
 */
@Service("fileService")
public class FileServiceImpl extends BaseServiceImpl implements FileService {
    @Resource
    private FileRepository fileRepository;

    @Override
    public ProjectFile upload(ProjectFile file) {
        return fileRepository.saveAndFlush(file);
    }

    @Override
    public ProjectFile getFile(String uuid) {
        return fileRepository.getProjectFileByUuid(uuid);
    }

    @Override
    public ProjectFile getFile(long id) {
        return fileRepository.getOne(id);
    }

    @Override
    public boolean isFileExist(long id) {
        return fileRepository.existsById(id);
    }

    @Override
    public void deleteFile(long id) {
        fileRepository.deleteById(id);
    }
}
