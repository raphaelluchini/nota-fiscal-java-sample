package com.devsolutions;

public final class TaxCalculator {
    //Value from this website: http://www.impostonanota.sebrae.com.br/index.php/tributacao/calculo
    //Using: UF: SP, Segmento: Comércio de Artigos do Vestuário, Regime: Simples Nacional, Faixa: Até 180.000,00

    //Values in %
    public static Double union = 2.75; //União - IRPJ, CSLL, COFINS, Pis/Pasep, CPP (tributos federais incluídos no Simples)
    public static Double state1 = 1.25; //Estado - ICMS (tributos estaduais incluídos no Simples)
    public static Double state2 = 0.00; //Estado - Substituição Tributária do ICMS
    public static Double stateTotal = state1 + state2; //Estado - Substituição Tributária do ICMS
    public static Double town = 0.00; //Município - ISS (não aplica para Comércio)
    public static Double taxPercentage = union + stateTotal + town; //Município - ISS (não aplica para Comércio)


    //Calula os impostos e soma com o total;
    public static Double calculateTax(int revenue){
        Double tax = (revenue * taxPercentage) / 100;
        return revenue + tax;
    }
}
