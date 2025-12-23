package fm.project.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fm.project.model.Tag;
import fm.project.repository.DAO.TagDAO;
import fm.project.repository.entities.TagEntity;
import fm.project.service.interfaces.ServiceInterface;

public class TagService implements ServiceInterface<TagEntity, Tag>{
    private TagDAO tagDAO = new TagDAO();

    @Override
    public Integer createEntity(TagEntity entity) {
        try {
            Integer newId = tagDAO.create(entity);
            return newId;
        } catch (SQLException e) {
            e.printStackTrace(); // replace with logger
                return -1;
        }
    }

    @Override
    public Optional<TagEntity> getEntityById(Integer id) {
        try {
            Optional<TagEntity> tagEntity = tagDAO.findById(id);
            if(tagEntity.isEmpty()) {
                throw new RuntimeException("Tag with id " + id + " not found");
            }
            return tagEntity;
        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<TagEntity> getAllEntities() {
        try {
            List<TagEntity> tagEntities = tagDAO.findAll();
            return tagEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TagEntity updateEntity(Integer id, TagEntity newEntity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try {
            Optional<TagEntity> tagEntity = tagDAO.findById(id);

            if (tagEntity.isEmpty()) {
                throw new RuntimeException("Tag with id " + id + " not found");
            }

            if(!(tagDAO.deleteById(id))) {
                throw new RuntimeException("Failed to delete tag with id " + id);
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Tag> convertEntityToModel(TagEntity entity) {
        Tag tag = new Tag();
        tag.setId(entity.getId());
        tag.setName(entity.getName());

        return Optional.of(tag);
    }

    @Override
    public Optional<Tag> getModelById(Integer id) {
        Optional<TagEntity> tagEntity = getEntityById(id);
        try {
            if(tagEntity.isPresent()) {
                Optional<Tag> tag = convertEntityToModel(tagEntity.get());

                if(tag.isPresent()) {
                    return tag;
                } else {
                    throw new RuntimeException("tagEntity conversion failed");
                }

            } else {
                throw new RuntimeException("tagEntity not found");
            }
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    

}
