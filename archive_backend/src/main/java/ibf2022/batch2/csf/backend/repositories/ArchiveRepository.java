package ibf2022.batch2.csf.backend.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Archive;

@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate template;

	public static final String BUNDLE_COLLECTION = "bundles";
	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	// db.bundles.insertOne(
	// 	{
	// 		bundleId: "123",
	// 		name: "test name",
	// 		date: "230988",
	// 		title: "test title",
	// 		comment: "test comment",
	// 		urls: ["testurl1", "testurl2", "testurl3"]
	// 	}
	// )
	public Archive recordBundle(Archive a) {
		return template.insert(a, BUNDLE_COLLECTION);
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	// 
	// db.bundles.find({bundleId: "1234"})
	public Document getBundleByBundleId(String bundleId) {
		Criteria criterial = Criteria.where("bundleId").is(bundleId);

		Query query = Query.query(criterial);

		Document result = (Document) template.find(query, Document.class, BUNDLE_COLLECTION);

		return result;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
