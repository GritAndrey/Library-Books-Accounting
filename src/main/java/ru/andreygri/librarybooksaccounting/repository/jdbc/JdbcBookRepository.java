package ru.andreygri.librarybooksaccounting.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {
    private static final RowMapper<Book> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Book.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBook;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcBookRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertBook = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("books")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    public Book save(Book book) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        if (book.isNew()) {
            Number newKey = insertBook.executeAndReturnKey(parameterSource);
            book.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE book SET  name=:name,year=:year,author=:author WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return book;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM book WHERE id=?", id) != 0;
    }

    @Override
    public Book get(int id) {
        List<Book> books = jdbcTemplate.query("SELECT * FROM book WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(books);
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY name", ROW_MAPPER);
    }
}
