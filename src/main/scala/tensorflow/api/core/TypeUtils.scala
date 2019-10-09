package tensorflow.api.core

object TypeUtils {
    import scala.compiletime.S 

    type If[Cond <: Boolean & Singleton, Then, Else] <: Then | Else = Cond match {
        case true => Then
        case false => Else
    }

    type +[A <: Int, B <: Int] <: Int = A match {
        case 0 => B
        case S[aMinusOne] => aMinusOne + S[B]
    }

    type *[A <: Int, B <: Int] <: Int = A match {
        case 0 => 0
        case _ => MultiplyLoop[A, B, 0]
    }

    private[TypeUtils] type MultiplyLoop[A <: Int, B <: Int, Res <: Int] <: Int = A match {
        case 0 => Res
        case S[aMinusOne] => MultiplyLoop[aMinusOne, B, B + Res]
    }

    type ==[A <: Int, B <: Int] <: Boolean = (A, B) match {
        case (0, 0) => true
        case (0, _) => false
        case (_, 0) => false
        case (S[aMinusOne], S[bMinusOne]) => aMinusOne == bMinusOne
    }
}
