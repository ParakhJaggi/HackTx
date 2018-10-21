export let Rank = {
	Ace: 1,
	King: 2,
	Queen: 3,
	Jack: 4,
	'10': 5,
	'9': 6,
	'8': 7,
	'7': 8,
	'6': 9,
	'5': 10,
	'4': 11,
	'3': 12,
	'2': 13
};
export let Suit = {Diamond: 1, Club: 2, Heart: 3, Spade: 4};

export let RankToWord = ['Zero', 'Ace', 'Two', 'Three', 'Four', 'Five', 'Six', 'Seven', 'Eight', 'Nine', 'Ten', 'Jack', 'Queen', 'King'];
export let SuitToWord = ['Zero', 'Diamonds', 'Clubs', 'Hearts', 'Spades'];

export class Card {
	constructor(rank, suit) {
		this.rank = rank;
		this.suit = suit;
	}

	getCardName = () => {
		return RankToWord[this.rank] + SuitToWord[this.suit];
	};
}