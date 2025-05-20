package edu.proyectocompiladores.demo.parser;
// Generated from AlgebraGrupo8.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AlgebraGrupo8Parser}.
 */
public interface AlgebraGrupo8Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AlgebraGrupo8Parser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(AlgebraGrupo8Parser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link AlgebraGrupo8Parser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(AlgebraGrupo8Parser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link AlgebraGrupo8Parser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(AlgebraGrupo8Parser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AlgebraGrupo8Parser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(AlgebraGrupo8Parser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link AlgebraGrupo8Parser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(AlgebraGrupo8Parser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link AlgebraGrupo8Parser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(AlgebraGrupo8Parser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpression(AlgebraGrupo8Parser.PowerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpression(AlgebraGrupo8Parser.PowerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpression(AlgebraGrupo8Parser.IdExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpression(AlgebraGrupo8Parser.IdExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDivExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpression(AlgebraGrupo8Parser.MulDivExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDivExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpression(AlgebraGrupo8Parser.MulDivExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpression(AlgebraGrupo8Parser.AddSubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpression(AlgebraGrupo8Parser.AddSubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpression(AlgebraGrupo8Parser.NumberExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpression(AlgebraGrupo8Parser.NumberExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(AlgebraGrupo8Parser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(AlgebraGrupo8Parser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bracketExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBracketExpression(AlgebraGrupo8Parser.BracketExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bracketExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBracketExpression(AlgebraGrupo8Parser.BracketExpressionContext ctx);
}