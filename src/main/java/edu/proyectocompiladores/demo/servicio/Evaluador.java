package edu.proyectocompiladores.demo.servicio;

import edu.proyectocompiladores.demo.parser.*;
import java.util.HashMap;
import java.util.Map;

public class Evaluador extends AlgebraGrupo8BaseVisitor<Double> {

    private final Map<String, Double> variables = new HashMap<>();

    public Map<String, Double> getVariables() {
        return variables;
    }

    @Override
    public Double visitProgram(AlgebraGrupo8Parser.ProgramContext ctx) {
        for (AlgebraGrupo8Parser.StatementContext statement : ctx.statement()) {
            visit(statement);
        }
        return null;
    }

    @Override
    public Double visitStatement(AlgebraGrupo8Parser.StatementContext ctx) {
        if (ctx.assignment() != null) {
            return visit(ctx.assignment());
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        return null;
    }

    @Override
    public Double visitAssignment(AlgebraGrupo8Parser.AssignmentContext ctx) {
        String nombreVariable = ctx.ID().getText();
        Double valor = visit(ctx.expression());
        variables.put(nombreVariable, valor);
        return valor;
    }

    @Override
    public Double visitAddSubExpression(AlgebraGrupo8Parser.AddSubExpressionContext ctx) {
        Double izquierdo = visit(ctx.expression(0));
        Double derecho = visit(ctx.expression(1));
        return ctx.getChild(1).getText().equals("+") ? izquierdo + derecho : izquierdo - derecho;
    }

    @Override
    public Double visitMulDivExpression(AlgebraGrupo8Parser.MulDivExpressionContext ctx) {
        Double izquierdo = visit(ctx.expression(0));
        Double derecho = visit(ctx.expression(1));
        return ctx.getChild(1).getText().equals("*") ? izquierdo * derecho : izquierdo / derecho;
    }

    @Override
    public Double visitPowerExpression(AlgebraGrupo8Parser.PowerExpressionContext ctx) {
        Double base = visit(ctx.expression(0));
        Double exponente = visit(ctx.expression(1));
        return Math.pow(base, exponente);
    }

    @Override
    public Double visitParenExpression(AlgebraGrupo8Parser.ParenExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double visitBracketExpression(AlgebraGrupo8Parser.BracketExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double visitIdExpression(AlgebraGrupo8Parser.IdExpressionContext ctx) {
        String nombreVariable = ctx.ID().getText();
        if (!variables.containsKey(nombreVariable)) {
            throw new RuntimeException("Variable no definida: " + nombreVariable);
        }
        return variables.get(nombreVariable);
    }

    @Override
    public Double visitNumberExpression(AlgebraGrupo8Parser.NumberExpressionContext ctx) {
        return Double.parseDouble(ctx.NUMBER().getText());
    }
}