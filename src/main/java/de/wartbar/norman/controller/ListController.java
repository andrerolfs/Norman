package de.wartbar.norman.controller;

import de.wartbar.norman.data.Constants;
import de.wartbar.norman.data.WebDefaults;
import de.wartbar.norman.model.jenkins.JenkinsClientStatus;
import de.wartbar.norman.spring.data.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
public class ListController {

    @Autowired
    DataBaseLists dataBaseLists;

    @Autowired
    DataBase db;

    @RequestMapping(value="/createlist", method = RequestMethod.POST)
    public ModelAndView postCreateList(@RequestParam Map<String,String> body) throws Exception {
        dataBaseLists.saveList(body);
        return getLists();
    }

    @RequestMapping(value="/createlist", method = RequestMethod.GET)
    public ModelAndView getCreateList(@RequestParam Map<String,String> body) throws Exception {
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "listcreate");
        return modelAndView;
    }

    @RequestMapping(value="/lists", method = RequestMethod.GET)
    public ModelAndView getLists() throws IOException {
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "lists");
        modelAndView.addObject("lists", dataBaseLists.getLists());
        return modelAndView;
    }

    @RequestMapping(value="/listusage", method = RequestMethod.GET)
    public ModelAndView getListUsage(@RequestParam Map<String,String> body) throws Exception {
        log.info("getListUsage : " + body.toString());
        Long listId = Long.parseLong(body.get(Constants.listId));
        Optional<ToDoPrimaryKeyListModel> listModel = dataBaseLists.getList(listId);
        List<ToDoPrimaryKeyItemModel> items = dataBaseLists.getItems(listId);
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "listusage");
        modelAndView.addObject("listName", listModel.get().getName());
        modelAndView.addObject("listId", listId);
        modelAndView.addObject("items", items);
        return modelAndView;
    }

    @RequestMapping(value="/createitem", method = RequestMethod.POST)
    public ModelAndView postCreateItem(@RequestParam Map<String,String> body) throws Exception {
        log.info("postCreateItem :" + body.toString());
        dataBaseLists.saveItem(body);
        return getListUsage(body);
    }

    @RequestMapping(value="/createitem", method = RequestMethod.GET)
    public ModelAndView getCreateItem(@RequestParam Map<String,String> body) throws Exception {
        log.info("getCreateItem :" + body.toString());
        Long listId = Long.parseLong(body.get(Constants.listId));
        Optional<ToDoPrimaryKeyListModel> listModel = dataBaseLists.getList(listId);
        List<ToDoPrimaryKeyItemModel> items = dataBaseLists.getItems(listId);
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "itemcreate");
        modelAndView.addObject("items", items);
        modelAndView.addObject("listId", listId);
        return modelAndView;
    }

    @RequestMapping(value="/edititem", method = RequestMethod.POST)
    public ModelAndView postEditItem(@RequestParam Map<String,String> body) throws Exception {
        log.info("postEditItem :" + body.toString());
        dataBaseLists.editItem(body);
        return getListUsage(body);
    }

    @RequestMapping(value="/edititem", method = RequestMethod.GET)
    public ModelAndView getEditItem(@RequestParam Map<String,String> body) throws Exception {
        log.info("getEditItem :" + body.toString());
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "itemedit");
        modelAndView.addObject("itemName", body.get(Constants.itemName));
        modelAndView.addObject("itemId", body.get(Constants.itemId));
        modelAndView.addObject("listId", body.get(Constants.listId));
        return modelAndView;
    }

    @RequestMapping(value="/removeitem", method = RequestMethod.POST)
    public ModelAndView postRemoveItem(@RequestParam Map<String,String> body) throws Exception {
        dataBaseLists.deleteListItem(body);
        return getListUsage(body);
    }

}
