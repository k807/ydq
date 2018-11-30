package group.ydq.service.service;

import group.ydq.model.entity.dm.ProjectFile;

/**
 * @author Daylight
 * @date 2018/11/30 19:57
 */
public interface FileService extends BaseService{
    void upload(ProjectFile file);

    ProjectFile getFile(String uniqueId);

    boolean isFileExist(String uniqueId);

    void deleteFile(String uniqueId);
}
