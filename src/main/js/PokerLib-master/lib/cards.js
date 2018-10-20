
var Rank = { Ace: 1, King: 2, Queen: 3, Jack: 4, '10': 5, '9': 6, '8': 7, '7': 8, '6': 9, '5': 10, '4': 11, '3': 12, '2': 13 };
var Suit = { Diamond: 1, Club: 2, Heart: 3, Spade: 4 };

var cards = [];

function Card(rank, suit) {
    this.rank = function () { return rank; }
    this.suit = function () { return suit; }
}

function createCard(rank, suit) {
    if (cards[rank] && cards[rank][suit])
        return cards[rank][suit];
    
    var card = new Card(rank, suit);
    
    if (!cards[rank])
        cards[rank] = [];
    
    cards[rank][suit] = card;
    
    return card;
}

module.exports = {
    card: createCard,
    
    Rank: Rank,
    Suit: Suit
};