package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.Image;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.InvalidMazeElementException;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Util;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * Implements the TileInfo class and contains the meta data on a tile (e.g Free or Door).
 *
 * @author straigfene
 *
 */
public class TileInfo_Impl implements TileInfo{
	private String name;
	private Image image;
	private Map<String, Object> fields;

	/**
	 * Constructor, takes all data required about the tile.
	 * An exception will be thrown if the data provided is invalid.
	 *
	 * @param name -name/type of the tile (e.g 'Free', 'ExitDoor', etc)
	 * @param image -the image for the tile
	 * @param fields -a map of any other fields in the tile object.
	 * 				 -the key = the name of the field as a string and the value = the value of the fields
	 * 				 -e.g for LockedDoor this will contain: key='id', value=id(int)
	 */
	public TileInfo_Impl(String name, Image image, Map<String, Object> fields) {
		this.name = name;
		this.image = image;
		if(!CheckValid(fields)) {
			throw new InvalidMazeElementException("Tile name and fields do not match: " + name + " " + fields );
		}
		this.fields = fields;
	}

	/**
	 * Check that the data provided is valid. That is that all fields needed are given.
	 * e.g if the name/type of item is LockedDoor it must be given the 'id' field.
	 * @param fields -a map of the field names and values
	 * @return return whether or not the data is valid
	 */
	private boolean CheckValid(Map<String, Object> fields) {

		MazeElementRegistry registry = MazeElementRegistry.getInstance();

		Set<Class<? extends Tile>> tileTypes = registry.getRegisteredTiles();

		//go through all registered actors and if the name matches this name check that all
		//fields of the registered actor are defined in the fields map
		boolean tileRegistered = false;
		for(Class<? extends Tile> tileType : tileTypes) {
			if(tileType.getSimpleName().equals(name)) {
				tileRegistered = true;

//				Field[] declaredFields = actorType.getDeclaredFields();
//
//				//go through all fields of actor at check that they are all defined in the fields
//				//map of this ActorInfo
//				for(int i=0; i<declaredFields.length; i++) {
//					Field declaredField = declaredFields[i];
//
//					//do not check exceptions (e.g the item field in Free which is described in the MazeState).
//					if(declaredField.getName().equals("item") && actorType.getSimpleName().equals("Free")) { continue; }
//
//					//fields being null is only invalid if the actorType has declared fields that are not exceptions
//					if(fields == null) { return false; }
//
//					if(!(fields.containsKey(declaredField.getName()))) { //check field exists
//						return false;
//					}
//					//check value of field is of the correct type
//					Class<?> valueClass = Util.toWrapperClass(fields.get(declaredField.getName()).getClass());
//					Class<?> type = Util.toWrapperClass(declaredField.getType());
//					if(!(type.isAssignableFrom(valueClass))) {
//						return false;
//					}
//				}

				//if there is a getRequiredFeilds method then invoke it and make sure this Actor info contains these
				//required fields otherwise continue.
				Map<String, Class<?>> requiredFields = null;
				try {
					requiredFields = (Map<String, Class<?>>) tileType.getMethod("getRequiredFeilds", new Class<?>[] {}).invoke(null, new Object[] {});
				} catch (IllegalAccessException e) {
					break;
				} catch (IllegalArgumentException e) {
					break;
				} catch (InvocationTargetException e) {
					break;
				} catch (NoSuchMethodException e) {
					break;
				} catch (SecurityException e) {
					break;
				}

				//fields being null is only invalid if the actorType has required fields
				if(requiredFields.size()>0 && fields == null) { return false; }

				//go through all required fields of actor at check that they are all defined in the fields
				//map of this ActorInfo
				for(String requiredField : requiredFields.keySet()) {
					if(!(fields.containsKey(requiredField))) { //check field exists
						return false;
					}

					//check value of field is of the correct type
					Class<?> valueClass = Util.toWrapperClass(fields.get(requiredField).getClass());
					Class<?> type = Util.toWrapperClass(requiredFields.get(requiredField));
					if(!(type.isAssignableFrom(valueClass))) {
						return false;
					}
				}

				break;

			}


		}

		//if the actor described by this ActorInfo is not registered (does not exist) then this
		//ActorInfo is not valid
		if(!tileRegistered) { return false; }

		return true;

	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getField(String fieldName) {
		if(fields == null) { return null; } //TODO throw error
		return fields.get(fieldName);
	}

	@Override
	public boolean hasField(String fieldName) {
		if(fields == null) { return false; }
		return fields.containsKey(fieldName);
	}

	@Override
	public Set<String> getFieldNames() {
		if(fields == null) { return new HashSet<String>(); }
		return fields.keySet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null || fields.isEmpty()) ? 0 : fields.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileInfo_Impl other = (TileInfo_Impl) obj;
		if (fields == null || fields.isEmpty()) {
			if (other.fields != null && !other.fields.isEmpty())
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TileInfo_Impl [name=" + name + ", fields=" + fields + "]";
	}



}
