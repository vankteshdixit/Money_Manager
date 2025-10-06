package vanktesh.example.Money_Manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vanktesh.example.Money_Manager.dto.CategoryDTO;
import vanktesh.example.Money_Manager.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")

public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO){
//        System.out.println("wfcghvdjbkhfvuiyudghvjbsdufyhcxjbhsudyfgvhcbxnjshduyfgvhcbnxjcdhufyghvcbn");
        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);

    }
}
