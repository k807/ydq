package group.ydq.service.service;

import group.ydq.model.entity.rbac.User;
import org.springframework.ui.Model;

public interface PageRenderService {
    void render(Model model, User user);
}
