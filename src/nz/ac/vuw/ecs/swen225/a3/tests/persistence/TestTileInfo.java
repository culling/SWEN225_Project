package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import java.awt.Image;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;

/**
 * A class used for testing that implements the TileInfo interface
 * 
 * @author cullingene
 *
 */
public class TestTileInfo implements TileInfo {
  /*
   * NOTE! The name will decide what fields are required and counted as vaild
   */
  String name = "Free";
  Image image = null;
  Map<String, Object> fields = new HashMap<String, Object>();

  TestTileInfo() {
  }

  TestTileInfo(String name, Image image, Map<String, Object> fields) {
    this.name = name;
    this.image = image;
    this.fields = fields;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Image getImage() {
    return image;
  }

  @Override
  public Object getField(String fieldName) {
    Object o = new Object();
    return o;
  }

  @Override
  public boolean hasField(String fieldName) {
    return (getFieldNames().contains(fieldName));
  }

  @Override
  public Set<String> getFieldNames() {
    return fields.keySet();
  }

  @Override
  public String toString() {
    return "TileInfo_Impl [name=" + name + ", fields=" + fields + "]";
  }

}
