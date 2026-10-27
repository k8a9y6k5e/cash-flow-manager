import 'package:flutter/material.dart';

class AppTheme {
  static ThemeData lightTheme = ThemeData(
      colorScheme: ColorScheme(
        brightness: Brightness.light,
        primary: Color(0xFF63EF79),
        onPrimary: Color(0xFF15251C),
        error: Color(0xFFEF6363),
        onError: Color(0xFF251515),
        secondary: Color(0xFFF6F8F6),
        onSecondary: Color(0xFF15251C),
        surface: Color(0xFFEFFBF1),
        onSurface: Color(0xFF0F0F0F),
      ),
      scaffoldBackgroundColor: Colors.white,
      fontFamily: "Inter",
      textTheme: TextTheme(
        displayLarge: TextStyle(
            fontSize: 42,
            color: Color(0xFF15251C),
            fontWeight: FontWeight.w700
        ),
        displayMedium: TextStyle(
            fontSize: 27,
            color: Color(0xFF15251C),
            fontWeight: FontWeight.w600
        ),
        headlineMedium: TextStyle(
            fontSize: 18,
            color: Color(0xFF15251C),
            fontWeight: FontWeight.w600
        ),
        titleMedium: TextStyle(
            fontSize: 17,
            color: Color(0xFF15251C),
        ),
        titleSmall: TextStyle(
            fontSize: 11,
            color: Color(0xFF15251C),
        ),
        bodyMedium: TextStyle(
            fontSize: 14,
            color: Color(0xFF0F0F0F),
        ),
        bodySmall: TextStyle(
            fontSize: 10,
            color: Color(0xFF7A837E),
        ),
      ),
      cardTheme: CardThemeData(
        color: Color(0xFF63EF79),
        elevation: 1,
        shadowColor: Color(0xFF15251C),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(25)
        ),
      )
  );
}
