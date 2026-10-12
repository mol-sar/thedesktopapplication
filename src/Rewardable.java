/* This interface defines the contract for a rewardable entity,
 including methods for managing reward points and redeeming rewards. */
// Contributed by Mel
// Tested by Siti, Mayar and Hazim

public interface Rewardable {

    void redeemRewards();// allows users to redeem points for rewards

    void trackRewards();// tracks progress towards rewards and milestones
}
