package edu.proyectocompiladores.demo.parser;
// Generated from AlgebraGrupo8.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AlgebraGrupo8Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AlgebraGrupo8Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AlgebraGrupo8Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(AlgebraGrupo8Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraGrupo8Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(AlgebraGrupo8Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraGrupo8Parser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(AlgebraGrupo8Parser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powerExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpression(AlgebraGrupo8Parser.PowerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpression(AlgebraGrupo8Parser.IdExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpression(AlgebraGrupo8Parser.MulDivExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpression(AlgebraGrupo8Parser.AddSubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpression(AlgebraGrupo8Parser.NumberExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpression(AlgebraGrupo8Parser.ParenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bracketExpression}
	 * labeled alternative in {@link AlgebraGrupo8Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketExpression(AlgebraGrupo8Parser.BracketExpressionContext ctx);
}