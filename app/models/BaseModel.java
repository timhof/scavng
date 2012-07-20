package models;

import javax.persistence.MappedSuperclass;

import play.db.jpa.Model;

@MappedSuperclass
public class BaseModel extends Model {

	@Override
	public boolean equals(Object o) {
		BaseModel model = (BaseModel) o;
		return this.id == model.id;
	}

}
