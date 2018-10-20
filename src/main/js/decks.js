
var cards = require('./cards');
var Rank = cards.Rank;
var Suit = cards.Suit;

function Deck() {
    var deck = [];

    for (var n1 in Suit) {
        var s = Suit[n1];
        
        for (var n2 in Rank) {
            var r = Rank[n2];
            
            deck.push(cards.card(r, s));
        }
    }
        
    this.size = function () { return deck.length; };
    
    this.contains = function (card) { return deck.indexOf(card) >= 0; };
}

function createDeck() {
    return new Deck();
}

module.exports = {
    deck: createDeck
};