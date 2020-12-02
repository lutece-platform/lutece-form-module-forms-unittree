package fr.paris.lutece.plugins.forms.modules.unittree.business.selection;

import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.Step;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;

public class UnitSelectionConfigValue
{
    private int _nIdConfigValue;
    private int _nIdConfig;
    private Step _step;
    private Question _question;
    private String _strValue;
    private Unit _unit;
    private int _nOrder;
    
    /**
     * @return the nIdConfigValue
     */
    public int getIdConfigValue( )
    {
        return _nIdConfigValue;
    }
    /**
     * @param nIdConfigValue the nIdConfigValue to set
     */
    public void setIdConfigValue( int nIdConfigValue )
    {
        _nIdConfigValue = nIdConfigValue;
    }
    /**
     * @return the nIdConfig
     */
    public int getIdConfig( )
    {
        return _nIdConfig;
    }
    /**
     * @param nIdConfig the nIdConfig to set
     */
    public void setIdConfig( int nIdConfig )
    {
        _nIdConfig = nIdConfig;
    }
    /**
     * @return the strValue
     */
    public String getValue( )
    {
        return _strValue;
    }
    /**
     * @param strValue the strValue to set
     */
    public void setValue( String strValue )
    {
        _strValue = strValue;
    }
    /**
     * @return the Order
     */
    public int getOrder( )
    {
        return _nOrder;
    }
    /**
     * @param nOrder the Order to set
     */
    public void setOrder( int nOrder )
    {
        _nOrder = nOrder;
    }
    /**
     * @return the step
     */
    public Step getStep( )
    {
        return _step;
    }
    /**
     * @param step the step to set
     */
    public void setStep( Step step )
    {
        _step = step;
    }
    /**
     * @return the question
     */
    public Question getQuestion( )
    {
        return _question;
    }
    /**
     * @param question the question to set
     */
    public void setQuestion( Question question )
    {
        _question = question;
    }
    /**
     * @return the unit
     */
    public Unit getUnit( )
    {
        return _unit;
    }
    /**
     * @param unit the unit to set
     */
    public void setUnit( Unit unit )
    {
        _unit = unit;
    }
}
