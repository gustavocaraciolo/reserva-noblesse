<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="reserva">
        <h2 class="jh-entity-heading" data-cy="reservaDetailsHeading">
          <span v-text="$t('reservaNoblesseApp.reserva.detail.title')">Reserva</span> {{ reserva.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('reservaNoblesseApp.reserva.dataHora')">Data Hora</span>
          </dt>
          <dd>
            <span v-if="reserva.dataHora">{{ $d(Date.parse(reserva.dataHora), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.reserva.notas')">Notas</span>
          </dt>
          <dd>
            <span>{{ reserva.notas }}</span>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.reserva.user')">User</span>
          </dt>
          <dd>
            {{ reserva.user ? reserva.user.login : '' }}
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.reserva.espaco')">Espaco</span>
          </dt>
          <dd>
            <div v-if="reserva.espaco">
              <router-link :to="{ name: 'EspacoView', params: { espacoId: reserva.espaco.id } }">{{ reserva.espaco.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="reserva.id" :to="{ name: 'ReservaEdit', params: { reservaId: reserva.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reserva-details.component.ts"></script>
