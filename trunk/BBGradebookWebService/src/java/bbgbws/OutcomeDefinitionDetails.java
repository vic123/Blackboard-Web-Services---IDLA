/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */

package bbgbws;

//blackboard
import blackboard.data.gradebook.impl.OutcomeDefinition;

/**
 *
 * @author Andrew.Martin@ncl.ac.uk
 */
public class OutcomeDefinitionDetails implements ReturnTypeInterface
        
{
    private String asiDataBbId;
    private String category;
    private String description;
    private Integer numOfOutcomes;
    private String outcomeDefinitionBbId;
    private Integer position;
    private String title;
    private Float weight;

    public OutcomeDefinitionDetails(){}
    public OutcomeDefinitionDetails(OutcomeDefinition od)
    {
        this.asiDataBbId = od.getAsiDataId().toExternalString();
        this.category = od.getCategory().getDescription();
        this.description = od.getDescription();
        this.numOfOutcomes = od.getOutcomeCount();
        this.outcomeDefinitionBbId = od.getId().getExternalString();
        this.position = od.getPosition();
        this.title = od.getTitle();
        this.weight = od.getWeight();
    }
    
    public String getAsiDataBbId()
    {
	return this.asiDataBbId;
    }

    public void setAsiDataBbId(String asiDataBbId)
    {
	this.asiDataBbId = asiDataBbId;
    }

    public String getCategory()
    {
	return this.category;
    }

    public void setCategory(String category)
    {
	this.category = category;
    }

    public String getDescription()
    {
	return this.description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public Integer getNumOfOutcomes()
    {
	return this.numOfOutcomes;
    }

    public void setNumOfOutcomes(Integer numOfOutcomes)
    {
	this.numOfOutcomes = numOfOutcomes;
    }

    public String getOutcomeDefinitionBbId()
    {
	return this.outcomeDefinitionBbId;
    }

    public void setOutcomeDefinitionBbId(String outcomeDefinitionBbId)
    {
	this.outcomeDefinitionBbId = outcomeDefinitionBbId;
    }

    public Integer getPosition()
    {
	return this.position;
    }

    public void setPosition(Integer position)
    {
	this.position = position;
    }

    public String getTitle()
    {
	return this.title;
    }

    public void setTitle(String title)
    {
	this.title = title;
    }

    public Float getWeight()
    {
	return this.weight;
    }

    public void setWeight(Float weight)
    {
	this.weight = weight;
    }
    
    public String[] toStringArray()
    {
        return new String[]{
            this.outcomeDefinitionBbId,
            this.title,
            Float.toString(this.weight),
            this.category,
            this.description,
            Integer.toString(this.numOfOutcomes),
            Integer.toString(this.position)
        };
    }
    
    public String[] toStringArrayHeader()
    {
        return new String[]{"OutcomeDefinitionBbId","Title",
                    "Weight","Category","Description",
                    "Num of outcomes","Position"};
    }
}