/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmd;

import java.io.Serializable;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.PropertyChange;


/**
 * @author Joram Barrez
 */
public class DeleteProcessInstanceCmd implements Command<Void>, Serializable {

  private static final long serialVersionUID = 1L;
  protected String processInstanceId;
  protected String deleteReason;
  protected boolean skipCustomListeners;

  public DeleteProcessInstanceCmd(String processInstanceId, String deleteReason, boolean skipCustomListeners) {
    this.processInstanceId = processInstanceId;
    this.deleteReason = deleteReason;
    this.skipCustomListeners = skipCustomListeners;
  }

  public Void execute(CommandContext commandContext) {
    if(processInstanceId == null) {
      throw new BadUserRequestException("processInstanceId is null");
    }

    commandContext
      .getExecutionManager()
      .deleteProcessInstance(processInstanceId, deleteReason, false, skipCustomListeners);

    commandContext.getOperationLogManager()
      .logProcessInstanceOperation(UserOperationLogEntry.OPERATION_TYPE_DELETE, processInstanceId,
          null, null, PropertyChange.EMPTY_CHANGE);

    return null;
  }

}
