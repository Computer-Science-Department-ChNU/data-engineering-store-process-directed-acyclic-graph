package ua.edu.chnu.kkn.dag.familytree

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("author")
class AuthorController(
    @Autowired private val authorService: AuthorService
) {

    @GetMapping
    fun getAllAuthors(): List<Author> {
        return authorService.getAllAuthors()
    }

    @PostMapping
    fun addAuthor(@RequestBody author: AddAuthorBody): Author {
        return authorService.addAuthor(author)
    }

    @PutMapping
    fun updateAuthor(@RequestBody author: Author): Author? {
        return authorService.updateAuthor(author)
    }

    @DeleteMapping("/author/{authorId}")
    fun deleteAuthor(@PathVariable authorId: String) {
        authorService.deleteAuthor(authorId)
    }
}
