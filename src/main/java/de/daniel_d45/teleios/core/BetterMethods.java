/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;


public class BetterMethods {

    /**
     * This method compares the equality of two objects. If both are null, it outputs true.
     *
     * @param obj1 [Object] The first object
     * @param obj2 [Object] The second object
     * @return [boolean] Whether both objects are null or equal
     */
    public static boolean betterEquals(Object obj1, Object obj2) {
        try {

            if (obj1 == null && obj2 == null) {
                MessageMaster.sendExitMessage("BetterMethods", "betterEquals(" + obj1 + ", " + obj2 + ")", "success");
                return true;
            }

            boolean equal = obj1.equals(obj2);

            MessageMaster.sendExitMessage("BetterMethods", "betterEquals(" + obj1 + ", " + obj2 + ")", "success");
            return equal;
        } catch (NullPointerException e) {
            // One of the objects is null, the other is not
            MessageMaster.sendExitMessage("BetterMethods", "betterEquals(" + obj1 + ", " + obj2 + ")", "success");
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("BetterMethods", "betterEquals(" + obj1 + ", " + obj2 + ")", e);
            return false;
        }

    }

}
