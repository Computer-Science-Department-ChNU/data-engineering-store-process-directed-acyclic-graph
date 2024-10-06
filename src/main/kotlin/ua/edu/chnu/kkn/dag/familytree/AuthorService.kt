package ua.edu.chnu.kkn.dag.familytree

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorService {

    @Autowired
    lateinit var authorRepository: AuthorRepository

    fun getAllAuthors(): List<Author> {
        return authorRepository.findAll()
    }

    fun addAuthor(authorBody: AddAuthorBody): Author {
        return authorRepository.save(mapAuthorBody(authorBody))
    }

    private fun mapAuthorBody(author: AddAuthorBody): Author {
        return Author(
            UUID.randomUUID().toString(),
            author.name,
            author.books.map { bookTitle ->
                Book(
                    UUID.randomUUID().toString(),
                    bookTitle
                )
            }
        )
    }

    fun updateAuthor(author: Author): Author? {
        val authorFromDB: Optional<Author> = authorRepository.findById(author.id)
        if (authorFromDB.isPresent) {
            val authorFromDBVal: Author = authorFromDB.get()
            authorFromDBVal.books = author.books
            authorFromDBVal.name = author.name
            authorRepository.save(authorFromDBVal)
            return authorFromDB.get()
        } else {
            return null
        }
    }

    fun deleteAuthor(id: String) {
        authorRepository.deleteById(id)
    }
}