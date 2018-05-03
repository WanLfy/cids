package cn.edu.zzu.controller.base;

import cn.edu.zzu.util.Nodes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinhao on 2018/5/3.
 */
@Controller
@RequestMapping(value = {"/menu/"})
public class MenuController {

    @RequestMapping(value = "getMenuTree.json")
    @ResponseBody
    public List<Nodes> getMenuTree() {
        List<Nodes> list = new ArrayList<Nodes>();
        Nodes nodes = new Nodes();
        nodes.setText("一级菜单1");
        nodes.setHref("www.baidu.com");

        Nodes nodesc = new Nodes();
        nodesc.setText("一级菜单2");
        nodesc.setHref("xxxxx");
        list.add(nodes);
        list.add(nodesc);
        return list;

    }
}
