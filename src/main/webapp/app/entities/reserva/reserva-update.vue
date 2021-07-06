<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="reservaNoblesseApp.reserva.home.createOrEditLabel"
          data-cy="ReservaCreateUpdateHeading"
          v-text="$t('reservaNoblesseApp.reserva.home.createOrEditLabel')"
        >
          Create or edit a Reserva
        </h2>
        <div>
          <div class="form-group" v-if="reserva.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="reserva.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.reserva.dataHora')" for="reserva-dataHora">Data Hora</label>
            <div class="d-flex">
              <input
                id="reserva-dataHora"
                data-cy="dataHora"
                type="datetime-local"
                class="form-control"
                name="dataHora"
                :class="{ valid: !$v.reserva.dataHora.$invalid, invalid: $v.reserva.dataHora.$invalid }"
                required
                :value="convertDateTimeFromServer($v.reserva.dataHora.$model)"
                @change="updateZonedDateTimeField('dataHora', $event)"
              />
            </div>
            <div v-if="$v.reserva.dataHora.$anyDirty && $v.reserva.dataHora.$invalid">
              <small class="form-text text-danger" v-if="!$v.reserva.dataHora.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.reserva.dataHora.ZonedDateTimelocal"
                v-text="$t('entity.validation.ZonedDateTimelocal')"
              >
                This field should be a date and time.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.reserva.notas')" for="reserva-notas">Notas</label>
            <input
              type="text"
              class="form-control"
              name="notas"
              id="reserva-notas"
              data-cy="notas"
              :class="{ valid: !$v.reserva.notas.$invalid, invalid: $v.reserva.notas.$invalid }"
              v-model="$v.reserva.notas.$model"
            />
            <div v-if="$v.reserva.notas.$anyDirty && $v.reserva.notas.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.reserva.notas.maxLength"
                v-text="$t('entity.validation.maxlength', { max: 140 })"
              >
                This field cannot be longer than 140 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.reserva.user')" for="reserva-user">User</label>
            <select class="form-control" id="reserva-user" data-cy="user" name="user" v-model="reserva.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="reserva.user && userOption.id === reserva.user.id ? reserva.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.reserva.espaco')" for="reserva-espaco">Espaco</label>
            <select class="form-control" id="reserva-espaco" data-cy="espaco" name="espaco" v-model="reserva.espaco">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="reserva.espaco && espacoOption.id === reserva.espaco.id ? reserva.espaco : espacoOption"
                v-for="espacoOption in espacos"
                :key="espacoOption.id"
              >
                {{ espacoOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.reserva.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./reserva-update.component.ts"></script>
