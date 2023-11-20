package com.chunjae.pro05.ctrl;


import com.chunjae.pro05.biz.RecommentService;
import com.chunjae.pro05.entity.Recomment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recomment/*")
public class recommentController {

    @Autowired
    private RecommentService recommentService;

    @GetMapping("/List")
    public String recommentList(Model model) {
        List<Recomment> recommentList = recommentService.recommentList();
        model.addAttribute("recommentList", recommentList);
        return "/recomment/recommentList";
    }

    @GetMapping("/Add")
    public String recommentform(){
        return "/recomment/recommentForm";
    };

    @PostMapping("/Add")
    public String recommentAdd(Recomment recomment){
        recommentService.recommentAdd(recomment);
        return "redirect:/recomment/List";
    };

    @GetMapping("/Del")
    public String recommentDel(int no){
        recommentService.recommentDel(no);
        return "redirect:/recomment/List";
    };

}
