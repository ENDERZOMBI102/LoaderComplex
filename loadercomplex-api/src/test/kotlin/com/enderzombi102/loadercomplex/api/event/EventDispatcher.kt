package com.enderzombi102.loadercomplex.api.event


internal class FontChangedEvent : Event() {
	val oldFontName: String = "Nirvana"
	var newFontName: String = "Times New Roman"


	override fun toString(): String {
		return String.format(
			"CaseChangedEvent{ oldFontName='%s', newFontName='%s' }",
			this.oldFontName,
			this.newFontName
		)
	}
}

internal object Test {
	private val FONT_CHANGED: EventDispatcher<FontChangedEvent> = EventDispatcher.create(
		FontChangedEvent::class.java
	)

	@JvmStatic
	fun main( argv: Array<String> ) {
		FONT_CHANGED.register { evt -> println( "lambda: %s".format( evt ) ) }
		FONT_CHANGED.register( ::onCaseChanged )
		FONT_CHANGED.invoke( FontChangedEvent() )
	}

	private fun onCaseChanged( evt: FontChangedEvent ) {
		println( "methodRef: %s".format( evt ) )
	}
}
