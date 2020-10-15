package user;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {
    public static String currentUser = null;

    public UserService() throws IOException {
        if(currentUser == null) {
            ArrayList<UserModel> allUsers = UserModel.getAllUsers();
            if(!allUsers.isEmpty()) currentUser = allUsers.get(0).getName();
        }
    }

    public String validateUserInput(String name, String age) throws IOException {
        String failReason = "";
        if( !this.isAlphanumeric(name) || name.isEmpty())
            failReason += "Name should only contain letters\n";
        int intAge = 0;

        try {
            intAge = Integer.parseInt(age);
            if(intAge < 0) failReason += "Age should be a positive integer\n";
        }
        catch (NumberFormatException  e) {
            failReason += "Age should be an integer\n";
        }
        return failReason;
    }

    public String createNewUser(String name, String status, String age) throws IOException {
        String failReason = validateUserInput(name, age);
        if(UserModel.userExist(name)) failReason += "User already exist\n";
        if( !failReason.equals("") ) return failReason;

        int intAge = Integer.parseInt(age);

        UserModel newUser = new UserModel(name, status, intAge);
        UserModel.addNewUsers(newUser);

        if( currentUser == null ) currentUser = name;

        return null;
    }

    public boolean isAlphanumeric(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<String> getAllUsers() throws IOException {
        ArrayList<UserModel> allUsers = UserModel.getAllUsers();
        ArrayList<String> allUserName = new ArrayList<>();

        for(UserModel u : allUsers) {
            allUserName.add(u.getName());
        }

        if (!allUserName.isEmpty() && allUserName.size() > 1) {
            int curIndex = allUserName.indexOf(currentUser);
            String temp = allUserName.get(0);
            allUserName.set(0, currentUser);
            allUserName.set(curIndex, temp);
        }

        return allUserName;
    }

    public UserModel getUser(String name) throws IOException {
        return UserModel.getUser(name);
    }

    public String editUser(String name, String status, String age) throws IOException {
        String failReason = validateUserInput(name, age);
        if(!failReason.equals("")) return failReason;

        if(!UserModel.userExist(name)) {
            return "User does not exist";
        }

        int intAge = Integer.parseInt(age);
        UserModel user = UserModel.getUser(name);
        UserModel.modifyUser(name, status, intAge, user.getCoord().x, user.getCoord().y);

        return null;
    }

    public String deleteUser(String name) throws IOException {
        ArrayList<UserModel> allUsers = UserModel.getAllUsers();
        if(allUsers.size() <= 1){
            return "Unable to delete the last user";
        }
        UserModel.deleteUser(name);
        if(UserModel.userExist(currentUser)) return null;

        allUsers = UserModel.getAllUsers();

        currentUser = allUsers.get(0).getName();
        return null;

    }
}
