// Equites, a simple chess interface
// Copyright © 2011 Frank S. Thomas <f.thomas@gmx.de>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package equites

sealed abstract class Action {
  var isChecking = false
  var isCheckmating = false
  var isStalemating = false
}

trait MoveLike extends Action {
  def piece: Piece
  def from: Square
  def to: Square

  def diff: Vector = to - from
  def l1Dist: Int = Square.l1Dist(from, to)
  def lInfDist: Int = Square.lInfDist(from, to)
}

trait CaptureLike extends MoveLike {
  require(piece isOpponentOf captured)

  def captured: Piece
  def capturedOn: Square = to
}

trait PromotionLike extends MoveLike {
  def piece: Pawn
  var newPiece: Piece = new Queen(piece.color)
}

case class Move(piece: Piece, from: Square, to: Square)
  extends MoveLike

case class Promotion(piece: Pawn, from: Square, to: Square)
  extends PromotionLike

case class Capture(piece: Piece, from: Square, to: Square, captured: Piece)
  extends CaptureLike

case class CaptureAndPromotion(piece: Pawn, from: Square, to: Square,
  captured: Piece)
  extends CaptureLike with PromotionLike

case class EnPassant(piece: Pawn, from: Square, to: Square,
  captured: Pawn, override val capturedOn: Square)
  extends CaptureLike


sealed abstract class Side
case object Kingside  extends Side
case object Queenside extends Side

sealed abstract class Castling(side: Side) extends Action {
  require(king isFriendOf rook)

  def king: King
  def rook: Rook

  val kingMove: Move = constructMove(king)
  val rookMove: Move = constructMove(rook)

  private def constructMove(piece: Piece): Move = {
    val (from, to) =
      Rules.castlingSquares((side, piece.color, piece.pieceType))
    Move(piece, from, to)
  }
}

case class CastlingShort(king: King, rook: Rook) extends Castling(Kingside)
case class CastlingLong (king: King, rook: Rook) extends Castling(Queenside)