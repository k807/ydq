package group.ydq.web;

import group.ydq.model.entity.cs.CheckStage;
import group.ydq.service.service.CheckStageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {

    @Resource
    private CheckStageService checkStageService;

    @Test
    public void contextLoads() {
        ArrayList<CheckStage> all = (ArrayList<CheckStage>) checkStageService.findAll();
        System.out.println(all);
    }

}
