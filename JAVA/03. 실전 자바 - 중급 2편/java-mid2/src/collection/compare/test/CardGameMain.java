package collection.compare.test;

public class CardGameMain {

    public static void main(String[] args) {
        Deck deck = new Deck();

        Player player1 = new Player("플레이어1");
        Player player2 = new Player("플레이어2");

        for (int i = 0; i < 5; i++) {
            player1.drawCard(deck);
            player2.drawCard(deck);
        }

        player1.showHand();
        player2.showHand();

        Player winner = getWinner(player1, player2);
        if (winner == null) {
            System.out.println("무승부");
        } else {
            System.out.println(winner.getName() + " 승리");
        }
    }

    public static Player getWinner(Player player1, Player player2) {
        int rankSum1 = player1.rankSum();
        int rankSum2 = player2.rankSum();

        if (rankSum1 > rankSum2) {
            return player1;
        } else if (rankSum1 < rankSum2) {
            return player2;
        }

        return null;
    }
}
