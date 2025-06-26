// DynamicDrinkServing.java
public class DynamicDrinkServing {
    public boolean checkDistance(double left, double right, double front, double back) {
        StringBuilder feedback = new StringBuilder();
            boolean safe = true;

            if (left < 1.0) {
                System.out.println("Too close on LEFT. Move RIGHT by " + (1.0 - left) + " meters.");
                safe = false;
            }
            if (right < 1.0) {
                System.out.println("Too close on RIGHT. Move LEFT by " + (1.0 - right) + " meters.");
                safe = false;
            }
            if (front < 1.0) {
                System.out.println("Too close in FRONT. Move BACK by " + (1.0 - front) + " meters.");
                safe = false;
            }
            if (back < 1.0) {
                System.out.println("Too close in BACK. Move FORWARD by " + (1.0 - back) + " meters.");
                safe = false;
            }

            return safe;
        }


}
