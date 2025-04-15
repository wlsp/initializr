package wlsp.tech.initializr.controller;

import org.springframework.web.bind.annotation.*;
import wlsp.tech.initializr.model.Student;

@RestController
@RequestMapping("/api/moin")
public class HelloController {

    @GetMapping
    public String sayMoin(){
        return "Moin moin World!";
    }

    @PostMapping
    public String sayMoinPost(@RequestBody Student value){
        return "Moin Moin, " + value.firstName()+" "+ value.lastName();
    }

    @GetMapping("/{id}")
    public String printValue(@PathVariable("id") String id){
        return "Moin your ID is: " + id;
    }

    @GetMapping("/search")
    public String printQuery(@RequestParam String query){
        return "You query is: " + query;
    }
}
