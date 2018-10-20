
var cards = require('../lib/cards');
var Rank = cards.Rank;
var Suit = cards.Suit;

exports['create card with rank and suit'] = function (test) {
    var card = cards.card(Rank.Ace, Suit.Spade);
    
    test.ok(card);
    test.equal(typeof card, 'object');
    test.equal(card.rank(), Rank.Ace);
    test.equal(card.suit(), Suit.Spade);
};

exports['card with same rank ans suit give the same object'] = function (test) {
    var card1 = cards.card(Rank.Ace, Suit.Spade);
    var card2 = cards.card(Rank.Ace, Suit.Spade);
    
    test.strictEqual(card1, card2);
};

