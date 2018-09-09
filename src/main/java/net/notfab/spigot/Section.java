package net.notfab.spigot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Section {

    /**
     * Get an object in the specified path.
     *
     * @param path - the path to search.
     * @return - the object if found, null otherwise.
     */
    Object get(String path);

    /**
     * Get an object in the specified path.
     *
     * @param path - the path to search.
     * @param def - the default object to return if null.
     * @return - the object if found, def otherwise.
     */
    Object get(String path, Object def);

    /**
     * Get a string in the specified path.
     *
     * @param path - the path to search.
     * @return - the string if found, null otherwise.
     */
    String getString(String path);

    /**
     * Get a string in the specified path.
     *
     * @param path - the path to search.
     * @param def - the default string to return if null.
     * @return - the string if found, def otherwise.
     */
    String getString(String path, String def);

    /**
     * Get an int in the specified path.
     *
     * @param path - the path to search.
     * @return - the int if found, null otherwise.
     */
    int getInt(String path);

    /**
     * Get an int in the specified path.
     *
     * @param path - the path to search.
     * @param def - the default int to return if null.
     * @return - the int if found, null otherwise.
     */
    int getInt(String path, int def);

    /**
     * Get a boolean in the specified path.
     *
     * @param path - the path to search.
     * @return - the boolean if found, null otherwise.
     */
    boolean getBoolean(String path);

    /**
     * Get a boolean in the specified path.
     *
     * @param path - the path to search.
     * @param def - the default boolean to return if null.
     * @return - the boolean if found, def otherwise.
     */
    boolean getBoolean(String path, boolean def);

    /**
     * Get a double in the specified path.
     *
     * @param path - the path to search.
     * @return - the double if found, null otherwise.
     */
    double getDouble(String path);

    /**
     * Get a double in the specified path.
     *
     * @param path - the path to search.
     * @param def - the default double to return if null.
     * @return - the double if found, def otherwise.
     */
    double getDouble(String path, double def);

    /**
     * Get a generic list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    List<?> getList(String path);

    /**
     * Get a generic list in the specified path.
     *
     * @param path - the path to search.
     * @param def - the default list to return if null.
     * @return - the list if found, def otherwise.
     */
    List<?> getList(String path, List<?> def);

    /**
     * Get a String list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<String> getStringList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<String> result = new ArrayList<>();
        for (Object object : list) {
            if ((object instanceof String) || (isPrimitiveWrapper(object))) {
                result.add(String.valueOf(object));
            }
        }
        return result;
    }

    /**
     * Get a Integer list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Integer> getIntegerList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Integer> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Integer) {
                result.add((Integer) object);
            } else if (object instanceof String) {
                try {
                    result.add(Integer.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((int) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).intValue());
            }
        }
        return result;
    }

    /**
     * Get a Boolean list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Boolean> getBooleanList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Boolean> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Boolean) {
                result.add((Boolean) object);
            } else if (object instanceof String) {
                if (Boolean.TRUE.toString().equals(object)) {
                    result.add(true);
                } else if (Boolean.FALSE.toString().equals(object)) {
                    result.add(false);
                }
            }
        }
        return result;
    }

    /**
     * Get a Double list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Double> getDoubleList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Double> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Double) {
                result.add((Double) object);
            } else if (object instanceof String) {
                try {
                    result.add(Double.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((double) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).doubleValue());
            }
        }
        return result;
    }

    /**
     * Get a Float list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Float> getFloatList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Float> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Float) {
                result.add((Float) object);
            } else if (object instanceof String) {
                try {
                    result.add(Float.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((float) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).floatValue());
            }
        }
        return result;
    }

    /**
     * Get a Long list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Long> getLongList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Long> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Long) {
                result.add((Long) object);
            } else if (object instanceof String) {
                try {
                    result.add(Long.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((long) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).longValue());
            }
        }
        return result;
    }

    /**
     * Get a Byte list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Byte> getByteList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Byte> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Byte) {
                result.add((Byte) object);
            } else if (object instanceof String) {
                try {
                    result.add(Byte.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((byte) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).byteValue());
            }
        }
        return result;
    }

    /**
     * Get a Character list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Character> getCharacterList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Character> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Character) {
                result.add((Character) object);
            } else if (object instanceof String) {
                String str = (String) object;
                if (str.length() == 1) {
                    result.add(str.charAt(0));
                }
            } else if (object instanceof Number) {
                result.add((char) ((Number) object).intValue());
            }
        }
        return result;
    }

    /**
     * Get a Short list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Short> getShortList(String path) {
        List<?> list = getList(path);
        if (list == null) {
            return new ArrayList<>(0);
        }
        List<Short> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Short) {
                result.add((Short) object);
            } else if (object instanceof String) {
                try {
                    result.add(Short.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((short) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).shortValue());
            }
        }
        return result;
    }

    /**
     * Get a generic Map list in the specified path.
     *
     * @param path - the path to search.
     * @return - the list if found, null otherwise.
     */
    default List<Map<?, ?>> getMapList(String path) {
        List<?> list = getList(path);
        List<Map<?, ?>> result = new ArrayList<>();
        if (list == null) {
            return result;
        }
        for (Object object : list) {
            if (object instanceof Map) {
                result.add((Map<?, ?>) object);
            }
        }
        return result;
    }

    /**
     * Creates a section in the specified path.
     *
     * @param path - the path to use.
     */
    void createSection(String path);

    /**
     * Gets a section of this configuration.
     *
     * @param path - the path to search.
     * @return - the section, null otherwise.
     */
    Section getSection(String path);

    /**
     * Check if this configuration contains the specified path.
     *
     * @param path - the path to search.
     * @return - if the path exists.
     */
    boolean contains(String path);

    /**
     * Removes a key from this configuration.
     *
     * @param path - the path to search.
     */
    void removeKey(String path);

    /**
     * Defines a value in the configuration.
     *
     * @param path - the path to use.
     * @param value - the value to set.
     */
    void set(String path, Object value);

    /**
     * Gets a list of all available keys in this section.
     *
     * @return - Set of all keys.
     */
    Set<String> getKeys();

    /**
     * Checks if a given object is a wrapper of a primitive.
     *
     * @param input - the object.
     * @return - if input is a primitive wrapper.
     */
    default boolean isPrimitiveWrapper(Object input) {
        return input instanceof Integer || input instanceof Boolean
                || input instanceof Character || input instanceof Byte
                || input instanceof Short || input instanceof Double
                || input instanceof Long || input instanceof Float;
    }

}