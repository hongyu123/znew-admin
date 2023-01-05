package com.hfw.api.controller;

import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.entity.SysContent;
import com.hfw.basesystem.enums.AppArticleEnum;
import com.hfw.basesystem.service.AppService;
import com.hfw.basesystem.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文章浏览控制器
 * @author zyh
 * @date 2022-11-25
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private AppService appService;
    @Autowired
    private SysService sysService;

    //浏览系统文章
    @GetMapping("/sys/{type}")
    public String sys(@PathVariable("type") AppArticleEnum.System type, Model model){
        AppArticle article = appService.appArticle(type.getCode());
        if(article!=null){
            model.addAttribute("title", article.getTitle());
            model.addAttribute("content", article.getContent());
        }
        return "h5";
    }

    //浏览图文详情
    @GetMapping("/graphic")
    public String graphic(@RequestParam Long id, Model model){
        SysContent content = sysService.sysContent(id);
        if(content!=null){
            model.addAttribute("content", content.getContent());
        }
        return "h5";
    }

}
