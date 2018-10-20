
var decks = require('../lib/decks');
var cards = require('../lib/cards');
var Suit = cards.Suit;
var Rank = cards.Rank;

exports['create deck with 52 cards'] = function (test) {
    var deck = decks.deck();
    
    test.ok(deck);
    test.equal(typeof deck, 'object');
    test.equal(deck.size(), 52);
};

exports['check card existence'] = function (test) {
    var deck = decks.deck();
    
    test.ok(deck);
    
    for (var n1 in Suit) {
        var s = Suit[n1];
        
        for (var n2 in Rank) {
            var r = Rank[n2];
            
            test.ok(deck.contains(cards.card(r, s)));
        }
    }
};
