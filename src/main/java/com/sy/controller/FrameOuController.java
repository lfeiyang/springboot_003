package com.sy.controller;

import com.github.pagehelper.PageHelper;
import com.sy.model.FrameOu;
import com.sy.service.IFrameOuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 部门控制层
 *
 * @author lfeiyang
 * @since 2022-04-30 23:26
 */
@RestController
@Scope(value = "singleton")
public class FrameOuController {
    @Autowired
    private IFrameOuService ouService;

    @RequestMapping("/selectByKey")
    public FrameOu selectByKey(@RequestParam String key) {
        System.out.println("1");
        return ouService.selectByKey(key);
    }

    @RequestMapping("/save")
    public int save(FrameOu frameOu) {
        return ouService.save(frameOu);
    }

    @RequestMapping("/updateNotNull")
    public int updateNotNull(FrameOu frameOu) {
        return ouService.updateNotNull(frameOu);
    }

    @RequestMapping("/updateAll")
    public int updateAll(FrameOu frameOu) {
        return ouService.updateAll(frameOu);
    }

    @RequestMapping("/delete")
    public int delete(String key) {
        return ouService.delete(key);
    }

    @RequestMapping("/batchDelete")
    public int batchDelete(@RequestParam("list") List<String> list, String property) {
        return ouService.batchDelete(list, property, FrameOu.class);
    }

    @RequestMapping("/getFrameOuList")
    public List<FrameOu> getFrameOuList(@RequestParam(defaultValue = "1") int first, @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(first, pageSize);

        return ouService.selectAll();
    }

    @RequestMapping("/selectByExample")
    public List<FrameOu> selectByExample() {
        Example example = new Example(FrameOu.class);
        example.createCriteria().andLike("ouCode", "370212%");

        return ouService.selectByExample(example);
    }
}
