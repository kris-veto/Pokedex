package com.apis.pokedex;

public class Details {
    private Stats[] stats;
    private Moves[] moves;

    public Stats[] getStats() {
        return stats;
    }

    public void setStats(Stats[] stats) {
        this.stats = stats;
    }

    public Moves[] getMoves() {
        return moves;
    }

    public void setMoves(Moves[] moves) {
        this.moves = moves;
    }

    public static class Stats {
        private int base_stat;
        private Stat stat;

        public int getBaseStat() {
            return base_stat;
        }

        public void setBaseStat(int base_stat) {
            this.base_stat = base_stat;
        }

        public Stat getStat() {
            return stat;
        }

        public void setStat(Stat stat) {
            this.stat = stat;
        }

        public static class Stat {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class Moves {
        private Move move;

        public Move getMove() {
            return move;
        }

        public void setMove(Move move) {
            this.move = move;
        }

        public static class Move {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
