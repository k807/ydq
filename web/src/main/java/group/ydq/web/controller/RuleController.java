package group.ydq.web.controller;

import group.ydq.model.dto.BaseResponse;
import group.ydq.model.entity.dm.DeclareRule;
import group.ydq.service.service.DeclareService;
import group.ydq.utils.DateUtil;
import group.ydq.utils.RetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daylight
 * @date 2018/12/14 19:16
 */
@Controller
@RequestMapping("/rule")
public class RuleController {
    @Resource
    private DeclareService declareService;

    @RequestMapping("/list")
    public String getRules(Model model){
        List<DeclareRule> rules=declareService.getRules();
        List<Map<String,Object>> list=new ArrayList<>();
        for (DeclareRule rule:rules){
            Map<String,Object> map=new HashMap<>();
            map.put("id",rule.getId());
            map.put("title",rule.getTitle());
            map.put("startTime", DateUtil.dateToStr(rule.getStartTime(),DateUtil.format2));
            map.put("endTime",DateUtil.dateToStr(rule.getEndTime(),DateUtil.format2));
            map.put("content",rule.getRuleContent());
            map.put("major",rule.getMajor());
            map.put("publisher",rule.getPublisher().getId());
            list.add(map);
        }
        model.addAttribute("list",list);
        return "ruleList";
    }

    @RequestMapping("/add")
    @ResponseBody
    public BaseResponse addRule(@RequestBody DeclareRule rule){
        declareService.addRule(rule);
        return RetResponse.success();
    }
}
