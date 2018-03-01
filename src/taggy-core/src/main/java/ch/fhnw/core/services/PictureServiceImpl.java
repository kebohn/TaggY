package ch.fhnw.core.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.fhnw.core.domain.Picture;
import ch.fhnw.core.domain.Tag;
import ch.fhnw.core.repository.PictureRepository;
@Transactional
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
	@Autowired
	PictureRepository pictRepository;

	@Override
	public Picture findByComment(String pictureComment) {
		return pictRepository.findByComment(pictureComment);
	}

	@Override
	public Picture findById(Integer id) {
		return pictRepository.findById(id);
	}

	@Transactional
	public void uptdatePicture(Picture pic) {
		Picture target = pictRepository.findOne(pic.getId());
		if(target == null){
			throw new RuntimeException("No Books found");
		}else{
			pictRepository.save(pic);
		}
	}

	@Override
	public Stream<Picture> findPictureByTags(Set<String> tags) {
		return findPictureByTags(tags);
	}

	@Override
	public List<Picture> findPictureByTagsAnd(List<Tag> tags) {
		List<Picture> picNames=new ArrayList<>();
		if(tags.size()==0){
			return null;
		}else if(tags.size()==1){
			return pictRepository.findByTagsIn(tags);
		}
		picNames=pictRepository.findByTags(tags.get(0));
		for(int i =1; i< tags.size() ;i++){
			List<Integer> ids = new ArrayList<>();
			for (Picture pic : picNames){
				ids.add(pic.getId());
			}
			System.out.println(ids);
			picNames= pictRepository.findByIdInAndTags(ids, tags.get(i));
		}
		return picNames;
	}

	

}
