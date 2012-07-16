public class User {
	public String userName;
	public String firstName;
	public String lastName;
	public String salt;
	public String password;
	public String crypt;

	public User (String passwd) {
		String[] matches = passwd.split(":");
		String[] name = matches[4].split(" ");

		userName = matches[0];
		firstName = name[0];
		lastName = name[1];
		salt = matches[1].substring(0, 2);
		crypt = matches[1];
	}

	public boolean attempt (String password) {
		String crypt = jcrypt.crypt(salt, password);

		if (crypt.equals(this.crypt)) {
			this.password = password;
			return true;
		}

		return false;
	}

	public String toString () {
		return userName + ": " + password;
	}
}
