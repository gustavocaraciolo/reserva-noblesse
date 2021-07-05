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
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.reserva.date')" for="reserva-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="reserva-date"
                  v-model="$v.reserva.date.$model"
                  name="date"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="reserva-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.reserva.date.$invalid, invalid: $v.reserva.date.$invalid }"
                v-model="$v.reserva.date.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.reserva.date.$anyDirty && $v.reserva.date.$invalid">
              <small class="form-text text-danger" v-if="!$v.reserva.date.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('reservaNoblesseApp.reserva.notes')" for="reserva-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="reserva-notes"
              data-cy="notes"
              :class="{ valid: !$v.reserva.notes.$invalid, invalid: $v.reserva.notes.$invalid }"
              v-model="$v.reserva.notes.$model"
            />
            <div v-if="$v.reserva.notes.$anyDirty && $v.reserva.notes.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.reserva.notes.maxLength"
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
            <label v-text="$t('reservaNoblesseApp.reserva.espaco')" for="reserva-espaco">Espaco</label>
            <select
              class="form-control"
              id="reserva-espaco"
              data-cy="espaco"
              multiple
              name="espaco"
              v-if="reserva.espacos !== undefined"
              v-model="reserva.espacos"
            >
              <option v-bind:value="getSelected(reserva.espacos, espacoOption)" v-for="espacoOption in espacos" :key="espacoOption.id">
                {{ espacoOption.nome }}
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
