# fastjson

官网地址: **https://github.com/Alibaba/fastjson/wiki/%E9%A6%96%E9%A1%B5**





# Encode

    import com.alibaba.fastjson.JSON;

    Group group = new Group();
    group.setId(0L);
    group.setName("admin");

    User guestUser = new User();
    guestUser.setId(2L);
    guestUser.setName("guest");

    User rootUser = new User();
    rootUser.setId(3L);
    rootUser.setName("root");

    group.addUser(guestUser);
    group.addUser(rootUser);

    String jsonString = JSON.toJSONString(group);

    System.out.println(jsonString);

# Output
    {"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}

# Decode
    String jsonString = ...;
    Group group = JSON.parseObject(jsonString, Group.class);

# Group.java
	public class Group {
	
	    private Long       id;
	    private String     name;
	    private List<User> users = new ArrayList<User>();
	
	    public Long getId() {
	        return id;
	    }
	
	    public void setId(Long id) {
	        this.id = id;
	    }
	
	    public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	
	    public List<User> getUsers() {
	        return users;
	    }
	
	    public void setUsers(List<User> users) {
	        this.users = users;
	    }

	    public void addUser(User user) {
	        users.add(user);
	    }
	}

# User.java
	public class User {
	
	    private Long   id;
	    private String name;
	
	    public Long getId() {
	        return id;
	    }
	
	    public void setId(Long id) {
	        this.id = id;
	    }
	
	    public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	}