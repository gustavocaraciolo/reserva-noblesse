<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apartamento">
        <h2 class="jh-entity-heading" data-cy="apartamentoDetailsHeading">
          <span v-text="$t('reservaNoblesseApp.apartamento.detail.title')">Apartamento</span> {{ apartamento.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('reservaNoblesseApp.apartamento.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ apartamento.numero }}</span>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.apartamento.torre')">Torre</span>
          </dt>
          <dd>
            <div v-if="apartamento.torre">
              <router-link :to="{ name: 'TorreView', params: { torreId: apartamento.torre.id } }">{{ apartamento.torre.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('reservaNoblesseApp.apartamento.user')">User</span>
          </dt>
          <dd>
            <span v-for="(user, i) in apartamento.users" :key="user.id"
              >{{ i > 0 ? ', ' : '' }}
              {{ user.login }}
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="apartamento.id"
          :to="{ name: 'ApartamentoEdit', params: { apartamentoId: apartamento.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./apartamento-details.component.ts"></script>
