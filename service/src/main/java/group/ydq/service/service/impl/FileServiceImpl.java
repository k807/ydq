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
    public void upload(ProjectFile file) {
        fileRepository.save(file);
    }

    @Override
    public ProjectFile getFile(String uniqueId) {
        return fileRepository.getByUniqueId(uniqueId);
    }

    @Override
    public boolean isFileExist(String uniqueId) {
        return fileRepository.existsByUniqueId(uniqueId);
    }

    @Override
    public void deleteFile(String uniqueId) {
        fileRepository.deleteByUniqueId(uniqueId);
        FileUtil.delete(uniqueId);
    }
}
