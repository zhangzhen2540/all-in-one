package indi.zz.test.model.web;

import indi.zz.test.model.dao.IPersonDao;
import indi.zz.test.model.entity.Person;
import indi.zz.test.model.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class PersonController {
    @Autowired
    private IPersonDao personDao;
    @Autowired
    private IPersonService personService;

    @GetMapping("/{id}")
    public Person get(@PathVariable Long id) {

        personService.add();


//        return personDao.getById(id);
        return null;
    }
}
