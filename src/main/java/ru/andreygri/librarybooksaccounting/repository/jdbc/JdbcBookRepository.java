package ru.andreygri.librarybooksaccounting.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.andreygri.librarybooksaccounting.model.Book;
import ru.andreygri.librarybooksaccounting.model.User;
import ru.andreygri.librarybooksaccounting.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcBookRepository implements BookRepository {
    private static final RowMapper<Book> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Book.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBook;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcBookRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertBook = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("book")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "author", "year");

    }

    @Override
    public Book save(Book book) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        if (book.isNew()) {
            Number newKey = insertBook.executeAndReturnKey(parameterSource);
            book.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE book SET  name=:name, author=:author, year=:year WHERE id=:id", parameterSource) == 0) {
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

    @Override
    public Optional<User> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT u.* FROM book join users u on u.id = book.user_id WHERE book.id=?"
                , BeanPropertyRowMapper.newInstance(User.class), id).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET user_id=NULL WHERE id=?", id);
    }

    public void assign(int id, User user) {
        jdbcTemplate.update("UPDATE Book SET user_id=? WHERE id=?", user.getId(), id);
    }
}
