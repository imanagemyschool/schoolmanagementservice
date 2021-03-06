package hasher

import scala.language.implicitConversions


/**
 * Companion for the Hash class
 */
object Hash {

    /**
     * Constructor...
     */
    def apply ( string: String ) = new Hash(string)

    /**
     * Implicitly converts from a hash to a string
     */
    implicit def hashToString ( from: Hash ): String = from.hex

    /**
     * Implicitly converts from a hash to a byte array
     */
    implicit def hashToByteArray ( from: Hash ): Array[Byte] = from.bytes

}

/**
 * Represents a hash
 */
case class Hash ( val bytes: Array[Byte] ) extends Equals {

    /**
     * Constructs a hash from a hex string
     */
    def this ( hex: String ) = this(
        hex.grouped(2).map( Integer.parseInt(_, 16).byteValue ).toArray
    )

    /**
     * Converts this hash to a hex encoded string
     */
    lazy val hex: String = bytes.map( "%02x".format(_) ).mkString("")

    /** {@inheritDoc} */
    override def toString: String = hex

    /** {@inheritDoc} */
    override def hashCode: Int = hex.hashCode

    /** {@inheritDoc} */
    override def equals ( other: Any ) = other match {
        case str: String
            => equals( Hash(str) )
        case hash: Hash if hash.canEqual(this)
            => Digest.compare( bytes, hash.bytes )
        case ary: Array[Byte]
            => Digest.compare( bytes, ary )
        case _ => false
    }

    /** {@inheritDoc} */
    override def canEqual ( other: Any ) = other.isInstanceOf[Hash]
}


